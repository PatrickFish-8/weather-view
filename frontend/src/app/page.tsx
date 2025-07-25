'use client';
import { useState } from 'react';
import UserInput from '@/components/UserInput';
import Loading from '@/components/Loading';
import WeatherOutput from '@/components/WeatherOutput';

export default function Home() {
  const [isLoading, setIsLoading] = useState(false);
  const [weather, setWeather] = useState(false);

  const handleSearch = async (city: string, state: string, country: string) => {
    setIsLoading(true);
    try {
      console.log(city, state, country);
      await new Promise(resolve => setTimeout(resolve, 3000));
      setWeather(true);
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  }

  return (
      <div className="flex flex-col items-center justify-center h-screen w-screen bg-[#777]">
        { weather ? (
          <WeatherOutput />
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
