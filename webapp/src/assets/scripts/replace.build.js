var replace = require('replace-in-file');
var buildDomain = process.argv[2];
const options = {
  files: 'config.js',
  from:  /domain: '(.*)'/g,
  to:  "domain: '"+ buildDomain + "'",
  allowEmptyPaths: false,
};
try {
  let changedFiles = replace.sync(options);
  if (changedFiles == 0) {
    throw "Please make sure that file '" + options.files + "' has \"Domain: ''\"";
  }
  console.log('Build Domain set: ' ,buildDomain);
}
catch (error) {
  console.error('Error occurred:', error);
  throw error
}
