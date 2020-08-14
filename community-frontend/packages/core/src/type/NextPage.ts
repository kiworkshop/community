import { NextComponentType, NextPageContext } from 'next';

interface DefaultCustomProps {
  namespacesRequired?: string[];
}

// TODO: Add redux store
type NextPageContextWithStore = NextPageContext;

export type NextPage<P = Record<string, unknown>, IP = P> =
  NextComponentType<NextPageContextWithStore, IP | DefaultCustomProps, P>;
