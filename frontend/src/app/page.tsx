'use client';
import { useState } from 'react';
import UserInput from '@/components/UserInput';
import Loading from '@/components/Loading';
import WeatherOutput from '@/components/WeatherOutput';

export default function Home() {
  const [isLoading, setIsLoading] = useState(false);
  const [weather, setWeather] = useState(false);
  const [isError, setIsError] = useState(false);

  const handleSearch = async (city: string, state: string, country: string) => {
    setIsError(false);
    setIsLoading(true);
    try {
      console.log(city, state, country);
      let response: any;
      if (state) {
        response = await fetch(`http://localhost:8080/api/entry?city=${city}&state=${state}&country=${country}`);
      } else {
        response = await fetch(`http://localhost:8080/api/entry?city=${city}&country=${country}`);
      }

      // checks response had no issue
      if (response.ok) {
        setWeather(true);
      } else {
        setIsError(true);
      }
    } catch (error) {
      console.error(error);
      setIsError(true);
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
            <>
              <UserInput onSearch={handleSearch}/>
              {isError && (
                <h1>Error Occurred</h1>
              )}
            </>
          )
        )}
      </div>
  );
}
