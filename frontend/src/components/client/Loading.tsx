export default function Loading() {
    return (
        <div className="flex flex-col items-center gap-[2vh]">
            <span
                className="flex justify-center items-center w-25 h-25 rounded-full animate-spin bg-linear-to-r from-[#6d90b9] to-[#BBC7DC]"
                style={{
                animationDuration: "1.5s"
                }}
            >
                <span
                    className="absolute w-[75%] h-[75%] rounded-full bg-[#777]"
                >
                </span>
            </span>
            <h1 className="text-[#BBC7DC]">Generating Content</h1>
        </div>
    )
}