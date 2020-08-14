# run in the directory which has node_modules.

ROOT_BIN_DIR=../../node_modules/.bin
THIS_BIN_DIR=node_modules/.bin

mkdir -p node_modules/.bin;
for i in $ROOT_BIN_DIR/*; do
  ln -sf ../../$ROOT_BIN_DIR/$(readlink $i) $THIS_BIN_DIR/${i##*/};
done
