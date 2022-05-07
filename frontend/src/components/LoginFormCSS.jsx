import styled from "styled-components";

export const FormContainer = styled.form`
    width: 100%;
    display: flex;
    flex-direction: column;
`;

export const Input = styled.input`
    width: 100%;
    height: 42px;
    outline: none;
    border: 1.4px solid rgba(200, 200, 200, 0.4);
    padding: 0px 10px;
    margin: 5px;
    transition: all 120ms ease-in-out;

    &::placeholder {
        color: rgba(200, 200, 200, 1);
    }

    &:focus {
        outline: none;
        border-bottom: 2px solid;
    }
`;

export const SubmitButton = styled.button`
    width: 95%;
    padding: 11px 40%;
    color: #fff;
    font-size: 15px;
    font-weight: 600;
    border: none;
    border-radius: 18px;
    cursor: pointer;
    transition: all, 240ms ease-in-out;
    background: rgba(0, 21, 255, 0.88);
    margin: 2px 5px;

    &:hover {
        filter: brightness(1.20);
        background: #000;
    }
`;

export const BoxContainer = styled.div`
    width: 280px;
    min-height: 100px;
    display: flex;
    flex-direction: column;
    border: double 5px transparent;
    border-radius: 19px;
    background-image: linear-gradient(white, white), 
                      linear-gradient(-45deg, purple, blue);
    background-origin: border-box;
    background-clip: content-box, border-box;
    
`;

export const TopContainer = styled.div`
    width: 100%;
    height: 50px;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    padding: 0 1.8em;
`;

export const HeaderContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
`;

export const HeaderText = styled.h2`
    font-size: 30px;
    font-weight: 600;
    line-height: 1.24;
    color: #000;
    z-index: 10;
    margin: 0;
    margin-top: 7px;
`;

export const InnerContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
`;

export const AppContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 17%;
`;