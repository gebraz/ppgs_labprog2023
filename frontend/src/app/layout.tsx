'use client';

import Sidebar from '@/components/Sidebar';
import './globals.css';
import { Inter } from 'next/font/google';
//theme
import "primereact/resources/themes/lara-light-indigo/theme.css";

//core
import "primereact/resources/primereact.min.css";
import { ReduxProvider } from '@/store/provider';
const inter = Inter({ subsets: ['latin'] });

export const metadata = {
  title: 'SPPG - Sistema de Pós-Graduação',
  description: ' Sistema de Pós-Graduação',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="pt-BR" className='h-full'>
      <body className={ `${inter.className} h-full` }>
        <ReduxProvider>
          <Sidebar />
          <div className='pl-64'>
            { children }
          </div>
        </ReduxProvider>

      </body>
    </html>
  );
}

