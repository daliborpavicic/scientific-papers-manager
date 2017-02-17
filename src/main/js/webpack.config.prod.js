import path from 'path';
import webpack from 'webpack';

const GLOBALS = {
  'process.env.NODE_ENV': JSON.stringify('production')
};

export default {
  resolve: {
    extensions: ['*', '.js', '.jsx', '.json']
  },
  devtool: 'source-map', // recommended for production, a bit slower build but highest quality source map experience
  entry: { // define an entry points of the application
    main: path.resolve(__dirname, 'src/index')
  },
  target: 'web', // can also be (node|electron)
  output: { // creates a bundle in memory and serves it to the browser
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/',
    filename: '[name].js' // file names match entry path object keys
  },
  plugins: [
    new webpack.DefinePlugin(GLOBALS),

    // Minify JS
    new webpack.optimize.UglifyJsPlugin()
  ],
  module: { // tell webpack which file types to handle
    loaders: [
      { test: /\.jsx?$/, exclude: /node_modules/, loaders: ['babel-loader'] }
    ]
  }
};
