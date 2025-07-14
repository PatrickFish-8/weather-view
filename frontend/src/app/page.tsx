import UserInput from '@/components/client/UserInput';
import TimeZone from '@/components/server/TimeZone';

export default function Home() {
  return (
      <div className="flex flex-col items-center justify-center h-screen w-screen bg-[#777]">
        <UserInput onLocationSubmit={(location: string) => {}}/>
      </div>
  );
}
