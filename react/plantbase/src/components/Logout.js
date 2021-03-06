import { Link, useHistory } from 'react-router-dom';
import React, {useContext} from 'react';
import CurrentUser from './contexts/CurrentUser';

function Logout () {
    const auth = useContext(CurrentUser);
    const history = useHistory();
    
    const handleLogout = (event) => {
        event.preventDefault();

        
        auth.logout();
        history.push('/');
    }

    return (
        <div>
            <Link onClick={handleLogout}className="btn btn-light nav-link nav-item dropdown" style={{color: 'rgba(89, 107, 93, 1)', fontFamily: 'Century Gothic'}}>Logout 🍂</Link>
        </div>
    );
}

export default Logout;