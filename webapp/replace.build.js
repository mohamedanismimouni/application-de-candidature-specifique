var replace = require('replace-in-file');
var buildDomain = process.argv[2];
const options = {
  files: 'src/assets/scripts/config.js',
  from:  /domain: '(.*)'/g,
  to:  "domain: '"+ buildDomain + "'",
  allowEmptyPaths: false,
};
try {
  let changedFiles = replace.sync(options);
  if (changedFiles == 0) {
    throw "Please make sure that file '" + options.files + "' has \"domain: ''\"";
  }
  console.log('Build domain set: ' + buildDomain);
}
catch (error) {
  console.error('Error occurred:', error);
  throw error
}