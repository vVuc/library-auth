FROM ubuntu:latest
LABEL authors="vulkyops"

ENTRYPOINT ["top", "-b"]