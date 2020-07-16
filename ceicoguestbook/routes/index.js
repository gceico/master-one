const _ = require('lodash')
const express = require('express')
const router = express.Router()
const azureStorage = require('azure-storage')
const tableService = azureStorage.createTableService()
const config = require('../utils/config')

router.get('/', function (req, res, next) {
    const query = new azureStorage.TableQuery().where('PartitionKey eq ?', config.partition)
    tableService.queryEntities(config.table, query, null, (err, result) => {
        if (err) {
            res.status(500)
            res.render('error', { error: err })
        }
        const entries = _.get(result, 'entries', [])
        const reviews = _.map(entries, (item) => ({
            key: _.get(item, 'RowKey._'),
            name: _.get(item, 'name._'),
            photo: _.get(item, 'photo._'),
            comment: _.get(item, 'comment._'),
            thumbnail: _.get(item, 'thumbnail._'),
        }))
        // console.log(reviews)
        res.render('index', { title: 'Guest Book', reviews: reviews })
    })
})

module.exports = router
