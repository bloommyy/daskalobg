import axios from 'axios';
import React, { useState } from 'react'
import { BoxContainer, FormContainer, Input, SubmitButton } from './common'

export function LoginForm(props) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    function btnOnClick(){
        axios.get()
    }

    return <BoxContainer>
        <FormContainer>
            <Input type="email" onChange={e => setEmail(e.target.value)} placeholder="E-mail" />
            <Input type="password" onChange={e => setPassword(e.target.value)} placeholder="Парола" />
        </FormContainer>
        <SubmitButton type="submit" onClick={btnOnClick}>Влез</SubmitButton>
    </BoxContainer>
}