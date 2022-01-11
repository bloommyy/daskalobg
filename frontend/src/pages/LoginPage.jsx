import React, { useState } from 'react';
import { AppContainer, BoxContainer, TopContainer, HeaderContainer, HeaderText, InnerContainer, FormContainer, Input, SubmitButton } from '../components/LoginFormCSS';
import { connect } from 'react-redux';
import {login} from '../actions/auth';


export default connect(({error}) => ({error}), { login })(props => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    function btnOnClick(){
        if (email === "" || password === "") {
            setError("Fields are required");
            return;
          }
        props.login({ email, password });
    }

    return (<AppContainer>
        <BoxContainer>
            <TopContainer>
                <HeaderContainer>
                    <HeaderText>Добре дошли!</HeaderText>
                </HeaderContainer>
            </TopContainer>
            <InnerContainer>
                    <FormContainer>
                        <Input type="email" onChange={e => setEmail(e.target.value)} placeholder="E-mail" />
                        <Input type="password" onChange={e => setPassword(e.target.value)} placeholder="Парола" />
                    </FormContainer>
                    <SubmitButton type="submit" onClick={btnOnClick}>Влез</SubmitButton>
            </InnerContainer>
        </BoxContainer>
        {error && (
            alert(error)
        )}
    </AppContainer>
    )
});
