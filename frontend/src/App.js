import styled from 'styled-components'
import { AccountBox } from './components';
import NavBar from './components/AppNavBar'

const AppContainer = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 15%;
`;

function App() {
    return <AppContainer>
        <AccountBox />
    </AppContainer>
}

export default App