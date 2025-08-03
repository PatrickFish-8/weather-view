import { Button } from '@mantine/core';

export default function WeatherOutput(
    { onReset } : { onReset: () => void }) {
    return (
        <div className="flex flex-col items-center gap-5 justify-center h-[100%] m-0 p-0">
            <div className="h-3/4 p-[1%] flex justify-center bg-[#4e5e53]/50">
                <img className="aspect-square" src="./generated_image.png"/>
            </div>
            <Button
                styles={{
                    root: {
                        backgroundColor: '#ff7034',
                        fontSize: "25px",
                        width: "75%",
                        borderColor: "#f2f0ef",
                        color: "#f2f0ef",
                        borderWidth: "2px",
                    },
                }}
                onClick={() => onReset()}
            >
                reset
            </Button>
        </div>
    )
}