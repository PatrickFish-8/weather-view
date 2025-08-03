import { useState, useEffect } from 'react';

export default function Loading() {
    const loading = ['Loading.', 'Loading..', 'Loading...', 'Loading..'];
    const [i, setI] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            setI(prev => (prev + 1) % loading.length);
        }, 400);

        return () => clearInterval(interval);
    }, []);

    return (
        <div className="flex flex-col items-center p-5 bg-[#4e5e53]/30 w-1/6 hover:bg-[#4e5e53]/50 hover:cursor-cell">
            <p className="text-5xl text-[#f2f0ef]">
                {loading[i]}
            </p>
        </div>
    )
}