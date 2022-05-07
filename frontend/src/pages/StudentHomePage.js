import Nav from '../components/StudentAppNavBar';
import { Button, Form } from '../components/HomePageCSS';
import { GetTable } from '../components/Table';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { TableType } from '../utils'

export function StudentHomePage() {
    const [grades, setGrades] = useState(false);
    const [absences, setAbsences] = useState(false);
    const [feedbacks, setFeedback] = useState(false);
    const userJSON = JSON.parse(localStorage.getItem('user'));

    const [gradesData, setGradesData] = useState('');
    const [absencesData, setAbsencesData] = useState('');
    const [feedbacksData, setFeedbacksData] = useState('');
    const [loaded, setLoaded] = useState(false);

    useEffect(() => {
        if (!loaded) {
            onGrades()
            setLoaded(true)
        }
    })

    function onGrades() {
        axios.get('http://localhost:8080/student/marks?stId=' + userJSON.uuid)
            .then(function (response) {
                if (typeof response.data === 'undefined')
                    return;

                setGradesData(response.data)
                setGrades(true);
                setAbsences(false);
                setFeedback(false);
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    function onAbsences() {
        axios.get('http://localhost:8080/student/absences?stId=' + userJSON.uuid)
            .then(function (response) {
                if (typeof response.data === 'undefined')
                    return;

                setAbsencesData(response.data);
                setGrades(false);
                setAbsences(true);
                setFeedback(false);
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    function onFeedback() {
        axios.get('http://localhost:8080/student/feedbacks?stId=' + userJSON.uuid)
            .then(function (response) {
                setFeedbacksData(response.data);
                setGrades(false);
                setAbsences(false);
                setFeedback(true);
            })
            .catch(function (error) {
                alert(error)
                return;
            })
    }

    return (
        <Form>
            <Nav />
            <Button onClick={onGrades} width='32.5%' selected={grades}>Оценки</Button>
            <Button onClick={onAbsences} width='32.5%' selected={absences}>Отсъствия</Button>
            <Button onClick={onFeedback} width='32.5%' selected={feedbacks}>Забележки</Button>
            {
                grades && <GetTable prepData={gradesData} type={TableType.StudentGrades} />
            }
            {
                absences && <GetTable prepData={absencesData} type={TableType.StudentAbsences} />
            }
            {
                feedbacks && <GetTable prepData={feedbacksData} type={TableType.StudentFeedbacks} />
            }
        </Form>
    )
}