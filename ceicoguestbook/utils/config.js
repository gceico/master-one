const config = {
    table: 'reviewsProd',
    queue: 'blobqueue',
    container: 'images',
    partition: 'develop',
    thumbsContainer: 'thumbs',
    storageAccount: 'guestsbookprod',
}

module.exports = config
