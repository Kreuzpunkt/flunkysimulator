module.exports = {
  entry: [
    './client/public/js/client.js'
  ],
  output: {
    path: __dirname + '/public/js',
    publicPath: '/js',
    filename: 'main.js'
  }
} 
