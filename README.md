# Mock-nifi

An https endpoint to which data can be posted.

Intended to be a bit like the NiFI endpoint which will be the eventual
destination of the dataworks nightly snapshot. Instead of receiveing the
snapshot and wrtng it to HDFS, mock-nifi writes it to the local filesystem where
it  is available for inspection.

## Running locally (on the command line or in an IDE)

Connections are mutuallly authenticated over TLS so a keystore and truststore
are needed.  To generate these, checkout data-key-service, in the resources
sub-directory there is a script ```generate-developer-certs.sh```. Run this and
then copy the file ```keystore.jks``` and ```truststore.jks``` to the resources
sub-directory of this project. Tese files should also be made available to any
client that wishes to use mock-nifi (e.g. an instance of ```snapshot-sender```
running on the command line).

Once the key stores are in place the app can be run from the command line thus:

``` bash
./gradlew bootRun
```

## Accessing from the command line

Te service can be accessed using curl. The key and certificate from out of the
keystore need to be available to curl. These can be fetched out of the keystore
using utilities in ```data-key-service``. To do this go to the ```resources```
sub-directory of data-key-service and then ...

``` bash
source ./environment
extract_pems keystore.jks
```

This will result in 2 files ```key.pem``` and ```certificate.pem`` in the
current directory. curl can then be used to access the mock-nifi service thus

``` bash
curl --cert certificate.pem:changeit \
    --key key.pem \
    -kw RESPONSE: %{http_code}\n \
    -X POST -H "Content-type: text/plain" \
    -H "Filename: abc.txt" \
    -H "Collection: addresses" \
    --data-binary "hello world" \
    https://localhost:5443/collection

```
