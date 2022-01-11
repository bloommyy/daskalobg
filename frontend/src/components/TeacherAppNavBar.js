import { Navbar, NavbarBrand, UncontrolledDropdown, DropdownMenu, DropdownItem, DropdownToggle } from 'reactstrap';
import LogoutIcon from '@mui/icons-material/Logout';
import { logout } from '../actions/auth';
import { connect } from 'react-redux';
import { useState } from 'react';

export default connect(null, { logout })(props => {

    const [selectedClass, setSelectedClass] = useState('Избери клас')

    function LogOut() {
        props.logout(localStorage.getItem('user'))
    }

    function OnChange(item) {
        props.selectedClassChanged(item)
        setSelectedClass(item)
    }

    return (<Navbar color="dark" dark fixed="top">
        <NavbarBrand position="absolute" left="50%" className='m-auto'>Училище</NavbarBrand>
        <UncontrolledDropdown className='dropdown' >
            <DropdownToggle className='dropdownToggle' caret>
                {selectedClass}
            </DropdownToggle>
            <DropdownMenu>
                {
                    props.classes.map(function (item, index, array) {
                        return (
                            <DropdownItem key={index} onClick={() => OnChange(item)} className='ddItem'>
                                {item}
                            </DropdownItem>
                        )
                    })
                }
            </DropdownMenu>
        </UncontrolledDropdown>
        <NavbarBrand margin-left='50px' >{JSON.parse(localStorage.getItem('user')).firstName}</NavbarBrand>
        <LogoutIcon sx={{ cursor: 'pointer', margin: '0 10px 0 0' }} onClick={LogOut} color='primary' />
    </Navbar>)
})