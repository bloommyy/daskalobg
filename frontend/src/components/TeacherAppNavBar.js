import { Navbar, NavbarBrand, UncontrolledDropdown, DropdownMenu, DropdownItem, DropdownToggle } from 'reactstrap';
import LogoutIcon from '@mui/icons-material/Logout';
import { logout } from '../actions/auth';
import { connect } from 'react-redux';
import { useState } from 'react';

export default connect(null, { logout })(({ classes }) => {

    const [selectedClass, setSelectedClass] = useState('Options')

    function LogOut() {
        props.logout(localStorage.getItem('user'))
    }

    return (<Navbar color="dark" dark fixed="top">
        <NavbarBrand className="m-auto">Училище</NavbarBrand>
        <UncontrolledDropdown className='dropdown' >
            <DropdownToggle className='dropdownToggle' caret>
                {selectedClass}
            </DropdownToggle>
            <DropdownMenu>
                <DropdownItem onClick={() => setSelectedClass('Option 1')} className='ddItem'>
                    Option 1
                </DropdownItem>
                <DropdownItem className='ddItem'>
                    Option 2
                </DropdownItem>
            </DropdownMenu>
        </UncontrolledDropdown>
        <NavbarBrand margin-left='50px' >{JSON.parse(localStorage.getItem('user')).firstName}</NavbarBrand>
        <LogoutIcon sx={{ cursor: 'pointer', margin: '0 10px 0 0' }} onClick={LogOut} color='primary' />
    </Navbar>)
})