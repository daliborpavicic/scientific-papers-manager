/* eslint-disable no-console */
import webpack from 'webpack';
import chalk from 'chalk';
import webpackConfig from '../webpack.config.dev';

const compiler = webpack(webpackConfig);
const loggingLevel = 'minimal';

compiler.watch({
  aggregateTimeout: 300, // wait so long for more changes
  poll: true // use polling instead of native watchers
}, (err, stats) => {
  if (err) {
    console.log(chalk.red(err));
    return 1;
  }

  console.log(`Webpack stats: \n${stats.toString(loggingLevel)}`);

  return 0;
});
