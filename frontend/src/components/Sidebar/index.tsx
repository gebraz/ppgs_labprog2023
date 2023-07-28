import Link from "next/link";
import { PiChalkboardTeacherBold, PiColumnsBold, PiSignOutBold, PiSunBold } from 'react-icons/pi';
export default function Sidebar() {
    return (

        <div className="max-w-2xl mx-auto fixed h-screen">

            <aside className="w-64 h-full" aria-label="Sidebar">
                <div className="flex flex-col justify-between px-3 py-4 overflow-y-auto rounded bg-gray-50 dark:bg-gray-800 h-full">
                    <ul className="space-y-2">
                        <li>
                            <Link href="/"
                                className="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700">
                                <PiColumnsBold className="h-6 w-6 text-gray-400" />
                                <span className="ml-3">Programas</span>
                            </Link>
                        </li>
                        <li>
                            <Link href="/producoes"
                                className="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700">
                                <PiChalkboardTeacherBold className="h-6 w-6 text-gray-400" />
                                <span className="flex-1 ml-3 whitespace-nowrap">Produções</span>

                            </Link>
                        </li>

                    </ul>
                    <ul className='pb-10'>
                        <li>
                            <Link href="#"
                                className="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700">
                                <PiSunBold className="h-6 w-6 text-gray-400" />
                                <span className="flex-1 ml-3 whitespace-nowrap">Tema Claro</span>

                            </Link>
                        </li>
                        <li>
                            <Link href="/login"
                                className="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700">
                                <PiSignOutBold className="h-6 w-6 text-gray-400" />
                                <span className="flex-1 ml-3 whitespace-nowrap">Sair</span>

                            </Link>

                        </li>


                    </ul>
                </div>

            </aside>
        </div>
    );
}