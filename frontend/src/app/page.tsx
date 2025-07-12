'use client'
import { HeroUIProvider } from "@heroui/react";
import UserInput from '../components/UserInput';

export default function Home() {
  return (
    <HeroUIProvider>
      <div className="flex flex-col items-center justify-center h-screen w-screen bg-white">
        <UserInput />
      </div>
    </HeroUIProvider>
  );
}
