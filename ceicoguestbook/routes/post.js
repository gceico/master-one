const express = require('express')
const getStream = require('into-stream')
const multer = require('multer')
const moment = require('moment')
const azureStorage = require('azure-storage')
const config = require('../utils/config')

const router = express.Router()
const inMemoryStorage = multer.memoryStorage()
const upload = multer({ storage: inMemoryStorage }).single('image')

const blobService = azureStorage.createBlobService()
const tableService = azureStorage.createTableService()

const handleError = (err, res) => {
    res.status = 500
    res.render('error', { error: err })
}

const getRowKey = () => {
    const now = moment().add(-2, 'hours').valueOf() * 10000 + 621355968000000000
    const maxTime = 3155378975999999999
    return (maxTime - now).toString()
}

router.post('/', upload, function (req, res, next) {
    // const key = Math.random().toString().replace(/0\./, '')
    const key = getRowKey()
    const blobName = `${key}-${req.file.originalname}`

    const stream = getStream(req.file.buffer)
    const streamLength = req.file.buffer.length

    blobService.createBlockBlobFromStream(config.container, blobName, stream, streamLength, (err) => {
        if (err) {
            handleError(err, res)
            return
        }
        const blobURL = `https://${config.storageAccount}.blob.core.windows.net/${config.container}/${blobName}`
        const thumbnailURL = `https://${config.storageAccount}.blob.core.windows.net/${config.thumbsContainer}/${blobName}`
        const entGen = azureStorage.TableUtilities.entityGenerator

        const entity = {
            PartitionKey: entGen.String(config.partition),
            RowKey: entGen.String(key),
            comment: entGen.String(req.body.comment),
            name: entGen.String(req.body.name),
            photo: blobURL,
            thumbnail: thumbnailURL,
        }
        tableService.insertEntity(config.table, entity, (err) => {
            if (err) {
                handleError(err, res)
                return
            }
            res.render('post')
        })
    })
})

module.exports = router
