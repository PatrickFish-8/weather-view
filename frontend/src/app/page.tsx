'use client';
import { useState } from 'react';
import UserInput from '@/components/UserInput';
import Loading from '@/components/Loading';

export default function Home() {
  const [isLoading, setIsLoading] = useState(false);
  const [weather, setWeather] = useState<any>(null);

  const handleSearch = async (location: string) => {
    setIsLoading(true);
    try {
      // const res = await fetch('');
      // const data = await res.json();
      // setWeather(data);
      await new Promise(resolve => setTimeout(resolve, 3000));
      setWeather(" ");
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  }

  return (
      <div className="flex flex-col items-center justify-center h-screen w-screen bg-[#777]">
        { weather ? (
          <div>Weather Info</div>
        ) : (
          isLoading ? (
            <Loading />
          ) : (
            <UserInput onSearch={handleSearch}/>
          )
        )}
      </div>
  );
}
