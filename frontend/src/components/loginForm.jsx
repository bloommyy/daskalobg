import React from 'react'
import styled from 'styled-components'
import { BoxContainer, FormContainer, Input, SubmitButton } from './common'

function btnOnClick(props){
    
}

export function LoginForm(props) {
    return <BoxContainer>
        <FormContainer>
            <Input type="email" placeholder="E-mail" />
            <Input type="password" placeholder="Парола" />
        </FormContainer>
        <SubmitButton type="submit" onClick={btnOnClick}>Влез</SubmitButton>
    </BoxContainer>
}