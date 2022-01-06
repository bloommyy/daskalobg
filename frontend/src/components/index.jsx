import React from "react";
import styled from "styled-components";
import { LoginForm } from "./loginForm";

const BoxContainer = styled.div`
    width: 280px;
    min-height: 100px;
    display: flex;
    flex-direction: column;
    border-radius: 19px;
    border: 5px solid  rgba(0, 21, 255, 0.68);
    box-shadow: 0 0 2px rgba(15, 15, 15, 0.28);
    position: relative;
    overflow: hidden;
`;

const TopContainer = styled.div`
    width: 100%;
    height: 50px;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    padding: 0 1.8em;
`;

const HeaderContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
`;

const HeaderText = styled.h2`
    font-size: 30px;
    font-weight: 600;
    line-height: 1.24;
    color: #000;
    z-index: 10;
    margin: 0;
    margin-top: 7px;
`;

const InnerContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
`;

const AppContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 25%;
`;

export function AccountBox(props){
    return (<AppContainer>
        <BoxContainer>
            <TopContainer>
                <HeaderContainer>
                    <HeaderText>Добре дошли!</HeaderText>
                </HeaderContainer>
            </TopContainer>
            <InnerContainer>
                <LoginForm />
            </InnerContainer>
        </BoxContainer>
    </AppContainer>
    )
}