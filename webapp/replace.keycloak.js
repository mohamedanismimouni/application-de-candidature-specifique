var replace = require('replace-in-file');
var buildKeycloak = process.argv[2];
const options = {
  files: 'src/assets/scripts/config.js',
  from:  /keycloak: '(.*)'/g,
  to:  "keycloak: '"+ buildKeycloak + "'",
  allowEmptyPaths: false,
};
try {
  let changedFiles = replace.sync(options);
  if (changedFiles == 0) {
    throw "Please make sure that file '" + options.files + "' has \keycloak config: ''\"";
  }
  console.log('Build keycloak set: ' ,buildKeycloak);
}
catch (error) {
  console.error('Error occurred:', error);
  throw error
}
