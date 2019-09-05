# Mock-nifi

An https endpoint to which data can be posted.

Intended to be a bit like the NiFi endpoint which will be the eventual
destination of the dataworks nightly snapshot. Instead of receiving the
snapshot and writing it to HDFS, mock-nifi writes it to the local filesystem
where it is available for inspection.

## Running locally (on the command line or in an IDE)

Connections are mutually authenticated over TLS so a keystore and truststore
are needed.  To generate these, checkout the ```data-key-service``` repository,
and in the resources sub-directory of this there is a script
```generate-developer-certs.sh```.  Run this and then copy the files
```keystore.jks``` and ```truststore.jks``` to  the resources sub-directory of
this project. These files should also be made available to any client that
wishes to use mock-nifi (e.g. an instance of ```snapshot-sender``` running on
the command line).

Once the keystores are in place the app can be run from the command line thus:

``` bash
SPRING_CONFIG_LOCATION=resources/application.properties ./gradlew bootRun
```

## Accessing from the command line

The service can be accessed using curl. The key and certificate from out of the
keystore need to be available to curl. These can be fetched out of the keystore
using utilities in ```data-key-service```. To do this go to the ```resources```
sub-directory of data-key-service and then ...

``` bash
source ./environment
extract_pems keystore.jks
```

This will result in 2 files ```key.pem``` and ```certificate.pem``` in the
current directory. curl can then be used to access the mock-nifi service thus

``` bash
curl --cert certificate.pem:changeit \
    --key key.pem \
    -k \
    -X POST -H "Content-type: text/plain" \
    -H "filename: abc.txt" \
    -H "collection: addresses" \
    --data-binary "hello world" \
    https://localhost:5443/collections

```
