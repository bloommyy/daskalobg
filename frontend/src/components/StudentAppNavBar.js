import { Navbar, NavbarBrand } from 'reactstrap';
import LogoutIcon from '@mui/icons-material/Logout';
import { logout } from '../actions/auth';
import { connect } from 'react-redux';

export default connect(null, { logout })(props => {

    function LogOut() {
        props.logout(localStorage.getItem('user'))
    }

    return (<Navbar color="dark" dark fixed="top">
        <NavbarBrand className="m-auto">Училище</NavbarBrand>
        <NavbarBrand margin-left='50px' >{JSON.parse(localStorage.getItem('user')).firstName}</NavbarBrand>
        <LogoutIcon sx={{ cursor: 'pointer', margin: '0 10px 0 0' }} onClick={LogOut} color='primary' />
    </Navbar>)
})