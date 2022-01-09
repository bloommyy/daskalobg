import styled from "styled-components";

export const Button = styled.button`
    margin-left: 10px;
    margin-top: 0px;
    width: 32.5%;
    height: 40px;
    border: 2px solid rgba(0, 21, 255, 0.68);;
    border-radius: 18px;
    font-weight: bold;

    &:hover{
        background: rgba(0, 21, 255, 1);
        color: #fff;
    }

    display:inline
`;

export const Form = styled.div`
    width: 100%;
    margin-top: 60px;
`;

export const Styles = styled.div`
  padding: 1rem;

  table {
    border-spacing: 0;
    border: 2px solid black;
    width: 100%;
    border-radius: 18px;

    tr {
      :last-child {
        td {
          border-bottom: 0;
        }
      }
    }

    th,
    td {
      margin: 0;
      padding: 0.5rem;
      border-bottom: 1px solid black;
      border-right: 1px solid black;
      
      :last-child {
        border-right: 0;
      }
    }
  }
`;

