import { TextInput, Button } from '@mantine/core'

export default function UserInput(
    { onSearch } : { onSearch: (location: string) => {} }) {
    const gradient = 'linear-gradient(45deg, #6d90b9 0%, #BBC7DC 100%)';

    return (
        <form
            className="flex w-3/5 flex-row justify-center items-center gap-4"
            onSubmit={(e) => {
                e.preventDefault();
                onSearch(e.currentTarget.value);
            }}
        >
            <TextInput
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
    )
}