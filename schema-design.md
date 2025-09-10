## MySQL Database Design

### Table: patients
- id: INT, Primary Key, Auto Increment
- firstName: VARCHAR(25), Not Null
- lastName: VARCHAR(25), Not Null\
- dob: DATE
- address: VARCHAR(255)
- phone: INT
- email: VARCHAR(100), UNIQUE
- gender: ENUM('Male', 'Female', 'Other')

### Table: doctors
- id: INT, Primary Key, Auto Increment
- firstName: VARCHAR(25)
- lastName: VARCHAR(25)
- specialization: VARCHAR(100)
- phone: INT

### Table: appointments
- id: INT, Primary Key, Auto Increment
- doctorId: INT, Foreign Key → doctors(id)
- patientId: INT, Foreign Key → patients(id)
- appointmentTime: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)

### Table: admin
- id: INT, Primary Key, Auto Increment
- username: VARCHAR(50), UNIQUE
- password_hash: VARCHAR(255) (never store raw passwords 🚨)
- role: ENUM('super_admin', 'staff_admin')

### Table: doctor_availability_template
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- day_of_week: ENUM('Mon','Tue','Wed','Thu','Fri','Sat','Sun'), Not Null
- start_time: TIME, Not Null
- end_time: TIME, Not Null
- slot_duration: INT, Not Null (duration in minutes)
- is_active: BOOLEAN, Default TRUE

### Table: doctor_availability_override
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- date: DATE, Not Null
- start_time: TIME, Not Null
- end_time: TIME, Not Null
- reason: VARCHAR(255), Nullable (e.g., vacation, emergency)
- is_active: BOOLEAN, Default TRUE


## MongoDB Collection Design

### Collection: prescriptions

```json
{
  "_id": "ObjectId('64abc123456')",
  "patientId": 101,
  "patientSnapshot": {
    "name": "John Smith",
    "age": 35
  },
  "appointmentId": 51,
  "issuedDate": "2025-09-10T09:30:00Z",

  "doctorId": 22, // link back to SQL doctors(id)
  "doctorSnapshot": {
    "name": "Dr. Sharma",
    "specialization": "Cardiology"
    // do you also want contact info here, or keep it minimal?
  },

  "medications": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "instructions": "Take 1 tablet every 6 hours"
    }
  ],

  "refill": {
    "totalAllowed": 2,
    "remaining": 1
  },

  "doctorNotes": "Patient complained of mild fever, advised rest.",

  "pharmacy": {
    "name": "Walgreens SF",
    "location": "Market Street"
  }
}
```

### Collection: feedback

```json
{
  "_id": "ObjectId('64fdb123456')",
  "patientId": 101,               // who gave the feedback
  "appointmentId": 55,            // was it tied to a specific visit?
  "doctorId": 22,                 // optional: feedback about a doctor
  "rating": 4,                    // 1–5 stars? thumbs up/down?
  "comment": "Doctor was very attentive and explained everything clearly.",
  "submittedAt": "2025-09-10T12:30:00Z"
}
```
### Collection: logs

//without payment
```json
{
  "_id": "ObjectId('64abc111111')",
  "userId": 101,
  "role": "patient",
  "event": "BOOKED_APPOINTMENT",
  "details": {
    "appointmentId": 55,
    "doctorId": 22
  },
  "timestamp": "2025-09-10T11:15:00Z"
}
```

//with payment
```json
{
  "_id": "ObjectId('64abc222222')",
  "userId": 101,
  "role": "patient",
  "event": "PAYMENT_SUCCESS",
  "details": {
    "appointmentId": 55,
    "doctorId": 22,
    "paymentId": "PAY12345"
  },
  "timestamp": "2025-09-10T11:16:00Z"
}
```

### Collection: messages

```json
{
  "_id": "ObjectId('64abc123456')",
  "doctorId": 12,
  "patientId": 45,
  "messages": [
    {
      "messageId": "uuid-1",
      "sender": "doctor",
      "text": "Please take the meds on time.",
      "status": "delivered",
      "attachment": null,
      "timestamp": "2025-09-10T10:15:00Z"
    },
    {
      "messageId": "uuid-2",
      "sender": "patient",
      "text": "Sure, thank you doctor!",
      "status": "read",
      "attachment": null,
      "timestamp": "2025-09-10T10:16:00Z"
    }
  ],
  "lastMessage": {
    "text": "Sure, thank you doctor!",
    "timestamp": "2025-09-10T10:16:00Z"
  },
  "updatedAt": "2025-09-10T10:16:00Z"
}
```
