import { BoxContainer, HeaderText, SubmitButton, Input } from './TeacherPageCSS';
import { DropDownMenu } from '../components/DropDownMenu';
import { DropDownMenuSpecialized } from './DropDownMenuSpecialized';
import "@progress/kendo-theme-default/dist/all.css";
import axios from 'axios';
import { useState } from 'react';
import { DropDownType } from '../utils';

export function AddNewMark({ students, updateData, addMark }) {

    var selectedPerson = '';
    var selectedMark = 0;

    const [selPerson, setSelPerson] = useState('');
    const [selMark, setSelMark] = useState('');

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })
    }

    function onMarkChange(value) {
        selectedMark = value
        setSelMark(value)
    }

    const marks = [2, 3, 4, 5, 6]
    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addMark() {

        let month = new Date().getMonth();
        let term = 1;

        if (month >= 1 && month <= 7)
            term = 2;

        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (selectedMark === '' && selMark === '') {
            alert("Не сте избрали оценка.")
            return;
        }

        axios.post('http://localhost:8080/teacher/mark/add?stId=' + (selectedPerson.id !== undefined ? selectedPerson.id : selPerson.id) + '&mark=' + (selectedMark != 0 ? selectedMark : selMark) + '&teacherId=' + userJSON.id + '&term=' + term)
            .then(function (response) {
                updateData(response.data)
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Изберете оценка</HeaderText>
            <DropDownMenu values={marks} onChange={onMarkChange} />
            <SubmitButton onClick={addMark}>Запиши оценка</SubmitButton>
        </BoxContainer>
    )
}

export function AddNewAbsence({ students, updateData }) {

    var selectedPerson = '';
    var isAbsence = null;

    const [selPerson, setSelPerson] = useState('')
    const [isAbs, setIsAbs] = useState(null)

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })
    }

    function onTypeChange(value) {
        isAbsence = value === 'Остъствие' ? true : false
        setIsAbs(value === 'Остъствие' ? true : false)
    }

    const absence = ['Остъствие', 'Закъснение']
    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addAbsence() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (isAbs === null && isAbsence === null) {
            alert("Не сте избрали тип на отсъствието.")
            return;
        }

        axios.post('http://localhost:8080/teacher/absence/add?&teacherId=' + userJSON.id + '&stId=' + (selectedPerson.id !== undefined ? selectedPerson.id : selPerson.id) + '&isAbsence=' + (isAbsence !== null ? isAbsence : isAbs))
            .then(function (response) {
                updateData(response.data)
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Тип</HeaderText>
            <DropDownMenu values={absence} onChange={onTypeChange} />
            <SubmitButton onClick={addAbsence}>Запиши остъствие</SubmitButton>
        </BoxContainer>
    )
}

export function AddNewFeedback({ students, updateData }) {

    var selectedPerson = '';
    var description = '';
    const [selPerson, setSelPerson] = useState('')
    const [desc, setDesc] = useState('')

    const userJSON = JSON.parse(localStorage.getItem('user'))

    function onPersonChange(value) {
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })
    }

    function onDescChange(value) {
        description = value;
        setDesc(value)
    }

    const studentNames = []

    students.map(function (item, index, array) {
        studentNames.push(item.names)
    })

    function addFeedback() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (description !== '' && desc !== '') {
            alert("Не сте въвели описание.")
            return;
        }

        axios.post('http://localhost:8080/teacher/feedback/add?teacherId=' + userJSON.id + '&stId=' + (selectedPerson.id !== undefined ? selectedPerson.id : selPerson.id) + '&description=' + (description !== '' ? description : desc))
            .then(function (response) {
                updateData(response.data)
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Напишете описание</HeaderText>
            <Input onChange={e => onDescChange(e.target.value)} />
            <SubmitButton top='66px' onClick={addFeedback}>Запиши забележка</SubmitButton>
        </BoxContainer>
    )
}

export function RemoveMark({ updateData, grades }) {
    var selectedPerson = '';
    var selectedMark = -1;

    const [selPerson, setSelPerson] = useState('');
    const [selMark, setSelMark] = useState(-1);

    const userJSON = JSON.parse(localStorage.getItem('user'))
    const [marks, setMarks] = useState([])

    const studentNames = []
    grades.map(function (item, index, array) {
        studentNames.push(item.studentNames)
    })

    function onPersonChange(value) {
        setMarks([])
        grades.map(function (item, index, array) {
            if (item.studentNames === value) {
                selectedPerson = item
                setSelPerson(item)
                let firstTermMarks = item.firstTerm.split(', ')
                let secondTermMarks = item.secondTerm.split(', ')
                let allMarks = firstTermMarks.concat(secondTermMarks)
                allMarks = allMarks.filter(n => n)
                let allMarkIndexes = item.firstTermIds.concat(item.secondTermIds)

                let temp = []
                for (let i = 0; i < allMarks.length; i++) {
                    temp.push({ id: allMarkIndexes[i], mark: allMarks[i] })
                }
                setMarks(temp)
            }
        })
    }

    function onMarkChange(value) {
        selectedMark = value
        setSelMark(value)
    }

    function removeMark() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }
        if (selectedMark === -1 && selMark === -1) {
            alert("Не сте избрали оценка.")
            return;
        }

        let link = 'http://localhost:8080/teacher/mark/delete?teacherId=' + userJSON.id + '&id=' + (selMark.id === undefined ? selectedMark : selMark.id)

        axios.delete(link)
            .then(function (response) {
                updateData(response.data)

                let temp = []
                marks.map(function (item, index, array) {
                    let id = selMark.id === undefined ? selectedMark : selMark.id
                    if (id !== item.id)
                        temp.push(item)
                })

                setMarks(temp)
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })


    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={studentNames} onChange={onPersonChange} />
            <HeaderText>Изберете оценка</HeaderText>
            <DropDownMenuSpecialized values={marks} type={DropDownType.Marks} onChange={onMarkChange} />
            <SubmitButton isForDelete={true} onClick={removeMark}>Изтрий оценка</SubmitButton>
        </BoxContainer>
    )
}

export function RemoveAbsence({ updateData, absences }) {
    var selectedPerson = '';

    const [selPerson, setSelPerson] = useState('');
    const [selAbsence, setSelAbsence] = useState([]);

    const userJSON = JSON.parse(localStorage.getItem('user'))
    const [dates, setDates] = useState([])

    let students = []
    let absencesData = []

    absences.map(function (item, index, array) {
        absencesData.push(item)
    })

    absencesData.map(function (item, index, array) {
        if (!students.includes(item.studentNames))
            students.push(item.studentNames)
    })

    function onPersonChange(value) {
        setSelPerson(value)
        selectedPerson = value

        setDates([])
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })

        let tempArr = []
        absences.map(function (item, index, array) {
            if (item.studentNames === value) {
                tempArr.push({ date: item.date, id: item.id })
            }
        })
        setDates(tempArr)
    }

    function onAbsenceChange(value) {
        setSelAbsence(value)
    }

    function removeAbsence() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (selAbsence === undefined) {
            alert("Не сте избрали отсъствие.")
            return;
        }

        axios.delete('http://localhost:8080/teacher/absence/delete?teacherId=' + userJSON.id + '&id=' + selAbsence.id)
            .then(function (response) {
                updateData(response.data)
                let temp = []
                dates.map(function (item, index, array) {
                    if (item.id != selAbsence.id)
                        temp.push(item)
                })
                setDates(temp)
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={students} onChange={onPersonChange} />
            <HeaderText>Изберете дата на </HeaderText>
            <DropDownMenuSpecialized values={dates} type={DropDownType.Absences} onChange={onAbsenceChange} />
            <SubmitButton isForDelete={true} onClick={removeAbsence}>Изтрий остъствие</SubmitButton>
        </BoxContainer>
    )
}

export function RemoveFeedback({ updateData, feedbacksData }) {
    var selectedPerson = '';

    const [selPerson, setSelPerson] = useState('');
    const [selFeedback, setSelFeedback] = useState([]);

    const [feedbacks, setFeedbacks] = useState([])

    const userJSON = JSON.parse(localStorage.getItem('user'))

    let students = []
    feedbacksData.map(function (item, index, array) {
        if (!students.includes(item.studentName))
            students.push(item.studentName)
    })

    function onPersonChange(value) {
        setSelPerson(value)
        selectedPerson = value

        setFeedbacks([])
        feedbacksData.map(function (item, index, array) {
            if (item.studentName === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })

        let tempArr = []
        feedbacksData.map(function (item, index, array) {
            if (item.studentName === value) {
                tempArr.push({ description: item.description, id: item.id })
            }
        })
        setFeedbacks(tempArr)
    }

    function onFeedbackChange(value) {
        setSelFeedback(value)
    }

    function removeFeedback() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (selFeedback === undefined) {
            alert("Не сте избрали забележка.")
            return;
        }

        axios.delete('http://localhost:8080/teacher/feedback/delete?teacherId=' + userJSON.id + '&id=' + selFeedback.id)
            .then(function (response) {
                updateData(response.data)
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={students} onChange={onPersonChange} />
            <HeaderText>Изберете забележка</HeaderText>
            <DropDownMenuSpecialized values={feedbacks} type={DropDownType.Feedbacks} onChange={onFeedbackChange} />
            <SubmitButton isForDelete={true} onClick={removeFeedback}>Изтрий забележка</SubmitButton>
        </BoxContainer>
    )
}

export function ExcuseAbsence({ updateData, absences }) {
    var selectedPerson = '';

    const [selPerson, setSelPerson] = useState('');
    const [selAbsence, setSelAbsence] = useState([]);

    const userJSON = JSON.parse(localStorage.getItem('user'))
    const [dates, setDates] = useState([])

    let students = []
    let absencesData = []

    absences.map(function (item, index, array) {
        absencesData.push(item)
    })

    absencesData.map(function (item, index, array) {
        if (!students.includes(item.studentNames))
            students.push(item.studentNames)
    })

    function onPersonChange(value) {
        setSelPerson(value)
        selectedPerson = value

        setDates([])
        students.map(function (item, index, array) {
            if (item.names === value) {
                selectedPerson = item
                setSelPerson(item)
            }
        })

        let tempArr = []
        absences.map(function (item, index, array) {
            if (item.studentNames === value && item.isExcused === 'Не' && item.type === 'Отсъствие') {
                tempArr.push({ date: item.date, id: item.id })
            }
        })
        setDates(tempArr)
    }

    function onAbsenceChange(value) {
        setSelAbsence(value)
    }

    function excuseAbsence() {
        if (selectedPerson === '' && selPerson === '') {
            alert("Не сте избрали ученик.")
            return;
        }

        if (selAbsence === undefined) {
            alert("Не сте избрали отсъствие.")
            return;
        }

        let isAbsence = true;
        let isExcused = false;
        dates.map(function (item, index, array) {
            absences.map(function (item1, index1, array1) {
                if (item.id === item1.id) {
                    if (item1.type !== 'Отсъствие')
                        isAbsence = false;

                    if (item1.isExcused === 'Да')
                        isExcused = true;
                }
            })
        })

        if (!isAbsence) {
            alert("Не можете да извините закъснение.")
            return;
        }

        if (isExcused) {
            alert("Не можете да извините извинено отсъствие.")
            return;
        }

        axios.put('http://localhost:8080/teacher/absence/excuse?teacherId=' + userJSON.id + '&id=' + selAbsence.id)
            .then(function (response) {
                updateData(response.data)
                let temp = []
                dates.map(function (item, index, array) {
                    if (item.id != selAbsence.id)
                        temp.push(item)
                })
                setDates(temp)
            })
            .catch(function (error) {
                alert(error.response.data)
                return;
            })
    }

    return (
        <BoxContainer>
            <HeaderText>Изберете ученик</HeaderText>
            <DropDownMenu values={students} onChange={onPersonChange} />
            <HeaderText>Изберете дата на </HeaderText>
            <DropDownMenuSpecialized values={dates} type={DropDownType.Absences} onChange={onAbsenceChange} />
            <SubmitButton isForDelete={false} onClick={excuseAbsence}>Извини остъствие</SubmitButton>
        </BoxContainer>
    )
}