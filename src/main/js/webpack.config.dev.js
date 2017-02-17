import path from 'path';

export default {
  resolve: {
    extensions: ['*', '.js', '.jsx', '.json'],
  },
  devtool: 'inline-source-map', // source maps are downloaded only if developer tools are opened (doesn't impact typical users)
  entry: { // define an entry points of the application
    main: path.resolve(__dirname, 'src/index')
  },
  target: 'web', // can also be (node|electron)
  output: { // creates a bundle in memory and serves it to the browser
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/',
    filename: '[name].js' // file names match entry path object keys
  },
  plugins: [],
  module: { // tell webpack which file types to handle
    loaders: [
      { test: /\.jsx?$/, exclude: /node_modules/, loaders: ['babel-loader'] }
    ]
  }
};
