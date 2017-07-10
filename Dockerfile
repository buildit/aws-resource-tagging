FROM anapsix/alpine-java:8_jdk

RUN \
  mkdir -p /aws && \
  apk -Uuv add groff less python py-pip && \
  pip install awscli && \
  apk --purge -v del py-pip && \
  rm /var/cache/apk/*

RUN apk update && apk add nodejs

RUN npm i serverless -g

CMD "/bin/sh"