const stream = require('stream')
const Jimp = require('jimp')

const storage = require('azure-storage')
const blobService = storage.createBlobService()

module.exports = (context, myBlob) => {
    context.log(
        'JavaScript blob trigger function processed blob \n Blob:',
        context.bindingData.blobTrigger,
        '\n Blob Size:',
        myBlob.length,
        'Bytes'
    )

    const widthInPixels = 220
    const blobName = context.bindingData.blobTrigger.split('/')[1]
    context.log(blobName)

    Jimp.read(myBlob)
        .then((thumbnail) => {
            thumbnail.resize(widthInPixels, Jimp.AUTO)

            thumbnail.getBuffer(Jimp.MIME_PNG, (err, buffer) => {
                const readStream = stream.PassThrough()
                readStream.end(buffer)

                blobService.createBlockBlobFromStream('thumbs', blobName, readStream, buffer.length, (err) => {
                    context.done()
                })
            })
        })
        .catch(context.log)
}
