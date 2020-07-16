const express = require('express')
const router = express.Router()

router.get('/', function (req, res, next) {
    const url = req.query.url
    console.log(url, req.query)
    res.render('photo', { url: url })
})

module.exports = router
