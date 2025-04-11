const { defineConfig } = require('eslint-define-config');

module.exports = defineConfig({
  languageOptions: {
    parser: require('@typescript-eslint/parser'),
    ecmaVersion: 2020,
    sourceType: 'module',
  },
  plugins: {
    '@typescript-eslint': require('@typescript-eslint/eslint-plugin'),
  },
  rules: {},
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'prettier',
  ],
  env: {
    node: true,
    es6: true,
  },
});
