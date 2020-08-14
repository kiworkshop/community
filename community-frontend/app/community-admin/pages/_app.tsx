import React from 'react';
import Head from 'next/head';
import { AppProps, AppContext, AppInitialProps } from 'next/app';
import { ThemeProvider } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import { NextComponentType } from 'next';
import theme from '../src/theme';

const MyApp: NextComponentType<AppContext, AppInitialProps, AppProps> = (props: AppProps) => {
  const { Component, pageProps } = props;

  React.useEffect(() => {
    // Remove the server-side injected CSS.
    const jssStyles = document.querySelector('#jss-server-side');
    if (jssStyles && jssStyles.parentElement) {
      jssStyles.parentElement.removeChild(jssStyles);
    }
  }, []);

  return (
    <>
      <Head>
        <title>My page</title>
        <meta name="viewport" content="minimum-scale=1, initial-scale=1, width=device-width" />
      </Head>
      <ThemeProvider theme={theme}>
        {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
        <CssBaseline />
        This is admin.
        <Component {...pageProps} />
      </ThemeProvider>
    </>
  );
};

export default MyApp;
