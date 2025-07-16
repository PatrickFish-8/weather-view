'use client';
import { TextInput, Button } from '@mantine/core'
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Loading from '@/components/client/Loading';

export default function UserInput() {
    const [location, setLocation] = useState('');
    const [isLoading, setIsLoading] = useState('true');
    const router = useRouter();
    const gradient =
        'linear-gradient(45deg, #6d90b9 0%, #BBC7DC 100%)';

    return (
        <>
        {!isLoading ?
            <form
                className="flex w-3/5 flex-row justify-center items-center gap-4"
                onSubmit={(e) => {
                    if(location != '') {
                        e.preventDefault();
                        router.push(`/?location=${encodeURIComponent(location)}`);
                        setLocation('');
                    }
                }}
            >
                <TextInput
                value={location}
                onChange={(e) => setLocation(e.currentTarget.value)}
                variant="default"
                size="lg"
                radius="xl"
                placeholder="Location (City, Country)"
                styles={{
                    root: {
                        backgroundImage: gradient,
                        borderRadius: '30px',
                        padding: '3px',
                        width: '75%',
                    },
                    input: {
                        color: 'var(--mantine-color-pink-filled',
                    },
                }}
                />
                <Button
                variant="default"
                type="submit"
                size="lg"
                radius="lg"
                styles={{
                    root: {
                        padding: 3,
                        border: 0,
                        backgroundImage: gradient,
                        width: '20%',
                        
                    },
                    inner: {
                        background: 'var(--mantine-color-body)',
                        color: 'var(--mantine-color-text)',
                        borderRadius: 'calc(var(--button-radius) - 2px)',
                        paddingLeft: 'var(--mantine-spacing-md)',
                        paddingRight: 'var(--mantine-spacing-md)',
                    },
                    label: {
                        backgroundImage: gradient,
                        WebkitBackgroundClip: 'text',
                        WebkitTextFillColor: 'transparent',
                    },
                    
                }}
                >
                Generate
                </Button>
            </form>
        :
            <Loading />
        }
        </>
    )
}