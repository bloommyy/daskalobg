import styled from "styled-components";

export const BoxContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 10px;
`;

export const FormContainer = styled.form`
    width: 100%;
    display: flex;
    flex-direction: column;
`;

export const MutedLink = styled.a`
    font-size: 12px;
    color: rgba(200, 200, 200, 0.8);
    font-weight: 500;
    text-decoration: none;
`;

export const Input = styled.input`
    width: 95%;
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
    background: rgba(0, 21, 255, 0.68);
    margin: 2px 20px;

    &:hover {
        filter: brightness(1.20);
        background: #000;
    }
`;