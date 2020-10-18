FROM debian:10-slim AS build
RUN apt-get update && apt-get install -y krb5-user

FROM gcr.io/distroless/java-debian10
COPY --from=build /usr/bin/kadmin /usr/bin/kadmin
COPY --from=build /lib/x86_64-linux-gnu/libss.so.* /lib/x86_64-linux-gnu/libcom_err.so.* /lib/x86_64-linux-gnu/libkeyutils.so.* /lib/x86_64-linux-gnu/
COPY --from=build /usr/lib/x86_64-linux-gnu/libkadm5clnt_mit.so.* /usr/lib/x86_64-linux-gnu/libgssrpc.so.* /usr/lib/x86_64-linux-gnu/*krb5*.so.* /usr/lib/x86_64-linux-gnu/libk5crypto.so.* /usr/lib/x86_64-linux-gnu/
