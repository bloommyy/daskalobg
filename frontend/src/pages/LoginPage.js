import React, { useState } from 'react';
import { AppContainer, BoxContainer, TopContainer, HeaderContainer, HeaderText, InnerContainer, FormContainer, Input, SubmitButton } from '../components/LoginFormCSS';
import { connect } from 'react-redux';
import { login } from '../actions/auth';
import MuiAlert from '@material-ui/lab/Alert';

function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}

export default connect(({ error }) => ({ error }), { login })(props => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    function btnOnClick() {
        if (email === "" || password === "") {
            alert("Не сте въвели e-mail или парола.");
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
            <Alert severity="error" onClick={() => setError(null)}>
                {props.error || error}
            </Alert>
        )
        }
    </AppContainer>
    )
});
