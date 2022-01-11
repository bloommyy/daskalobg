import { Form } from '../components/HomePageCSS';
import { StudentHomePage } from './StudentHomePage';
import { TeacherHomePage } from './TeacherHomePage';

function HomePage() {

    const userJSON = JSON.parse(localStorage.getItem('user'));
    let isStudent = (userJSON.stClass !== undefined) ? true : false;

    return (
        <Form>
            {isStudent && <StudentHomePage />}
            {!isStudent && <TeacherHomePage />}
        </Form>
    )
}

export default HomePage;