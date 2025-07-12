import { useState } from 'react';
import { Input } from "@heroui/react";

export default function UserInput() {

    return (
        <>
            <Input
                label="Email"
                type="email"
            />
        </>
    )
}