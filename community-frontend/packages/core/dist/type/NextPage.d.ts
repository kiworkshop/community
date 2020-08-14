import { NextComponentType, NextPageContext } from 'next';
interface DefaultCustomProps {
    namespacesRequired?: string[];
}
declare type NextPageContextWithStore = NextPageContext;
export declare type NextPage<P = Record<string, unknown>, IP = P> = NextComponentType<NextPageContextWithStore, IP | DefaultCustomProps, P>;
export {};
