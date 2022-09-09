const fs = require('fs');
const version = require('./package.json').version;

const iOSVersionFilePath = 'ios/Constants.m';
const androidVersionFilePath = 'android/src/main/java/com/letscooee/utils/Constants.java';


console.log(`updating to [${version}]`);
const newVersionCode = parseInt(version.split('.').map(v => v.padStart(2, '0')).join(''));

/**************** Write New Version in Constant.java & Constant.swift ***************/
bumpVersionInIOS(iOSVersionFilePath);
bumpVersionInAndroid(androidVersionFilePath);

/**************** End Write New Version in pubspec.yaml ***************/

/**
 * Access file at given path and write VERSION_NAME & VERSION_CODE
 *
 * @param path {string} path of the file
 */
function bumpVersionInAndroid(path) {
    let cooeeMetaData = fs.readFileSync(path, "utf8");
    cooeeMetaData = cooeeMetaData.replace(/VERSION_NAME = "[^"]+"/, `VERSION_NAME = "${version}"`);
    cooeeMetaData = cooeeMetaData.replace(/VERSION_CODE = [^\n]+/, `VERSION_CODE = ${newVersionCode};`);
    fs.writeFileSync(path, cooeeMetaData);
}


function bumpVersionInIOS(path) {
    let cooeeMetaData = fs.readFileSync(path, "utf8");
    cooeeMetaData = cooeeMetaData.replace(/VERSION_NAME = @"[^"]+"/, `VERSION_NAME = @"${version}";`);
    cooeeMetaData = cooeeMetaData.replace(/VERSION_CODE = [^\n]+/, `VERSION_CODE = ${newVersionCode};`);
    fs.writeFileSync(path, cooeeMetaData);
}
