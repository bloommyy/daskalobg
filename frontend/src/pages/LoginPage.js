import React, { useState } from 'react';
import { AppContainer, BoxContainer, TopContainer, HeaderContainer, HeaderText, InnerContainer, FormContainer, Input, SubmitButton } from '../components/LoginFormCSS';
import { connect } from 'react-redux';
import { login } from '../actions/auth';
import MuiAlert from '@material-ui/lab/Alert';
import PersonIcon from '@mui/icons-material/Person';
import LockIcon from '@mui/icons-material/Lock';

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
                    <div>
                        <div id='loginIcon'>
                            <PersonIcon fontSize='large' />
                        </div>
                        <div id='loginInput'>
                            <Input type="email" onChange={e => setEmail(e.target.value)} placeholder="E-mail" />
                        </div>
                    </div>
                    <div>
                        <div id='loginIcon'>
                            <LockIcon fontSize='large' />
                        </div>
                        <div id='loginInput'>
                            <Input type="password" onChange={e => setPassword(e.target.value)} placeholder="Парола" />
                        </div>
                    </div>
                </FormContainer>
                <SubmitButton type="submit" onClick={btnOnClick}>Влез</SubmitButton>
            </InnerContainer>
        </BoxContainer>
    </AppContainer>
    )
});
