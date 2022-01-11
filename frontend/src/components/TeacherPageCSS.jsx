import styled from "styled-components";

export const BoxContainer = styled.div`
    width: 98.7%;
    margin: 10px;
    height: 230px;
    border: 2px solid rgba(0, 21, 255, 0.68);
    border-radius: 18px;
`;

export const Input = styled.input`
    width: 99%;
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

export const HeaderText = styled.h2`
    font-size: 20px;
    font-weight: 600;
    line-height: 1.24;
    color: #000;
    z-index: 10;
    margin: 0;
    margin-top: 0.2%;
    margin-left: 1%;
`;

export const SubmitButton = styled.button`
    margin-left: 10px;
    margin-top: ${props => props.top};
    width: ${props => props.width};
    height: 40px;
    border: 2px solid black;
    border-radius: 18px;
    font-weight: bold;
    position: absolute;
    right: 0.9%;

    background: #0f0;
    color: ${props => (props.selected ? '#fff' : 'black')};

    &:hover{
        background: #000;
        color: #fff;
    }
`;
 
