/* eslint-disable no-console */
import webpack from 'webpack';
import chalk from 'chalk';
import webpackConfig from '../webpack.config.dev';

const loggingLevel = 'minimal';
const compiler = webpack(webpackConfig);

compiler.run((err, stats) => {
  if (err) {
    console.log(chalk.red(err));
    return 1;
  }

  console.log(`Webpack stats: \n${stats.toString(loggingLevel)}`);

  return 0;
});
