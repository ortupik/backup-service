package com.chris.security.impl;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.security.exceptions.BadRequestException;
import com.chris.security.exceptions.NotFoundException;
import com.chris.security.modal.CallLog;
import com.chris.security.modal.CallLogHolder;
import com.chris.security.modal.Contact;
import com.chris.security.modal.ContactHolder;
import com.chris.security.modal.TextMessage;
import com.chris.security.modal.TextThread;
import com.chris.security.modal.Texts;
import com.chris.security.repository.CallLogHolderRepository;
import com.chris.security.repository.ContactHolderRepository;
import com.chris.security.repository.TextsRepository;
import com.chris.security.service.BackupService;

@Service
public class BackupServiceImpl implements BackupService {

	@Autowired
	private TextsRepository textsRepository;

	@Autowired
	private ContactHolderRepository contactHolderRepository;

	@Autowired
	private CallLogHolderRepository callLogHolderRepository;

	@Override
	public Object findBackup(String resource, String deviceId) {
		if (resource.equals("texts")) {
			return findTexts(deviceId);
		} else if (resource.equals("contacts")) {
			return findContacts(deviceId);
		} else if (resource.equals("call-logs")) {
			return findCallLogs(deviceId);
		} else {
			throw new BadRequestException("Bad resource");
		}
	}

	public Object findCallLogs(String deviceId) {

		Optional<CallLogHolder> optional = callLogHolderRepository.findCallLogHolderByDeviceId(deviceId);

		if (optional.isEmpty()) {
			throw new NotFoundException();
		} else {
			CallLogHolder holder = optional.get();
			holder.getCallLogs().sort(Comparator.comparing(CallLog::getTimeCalled).reversed());
			return holder;
		}

	}

	public Object findContacts(String deviceId) {

		Optional<ContactHolder> optional = contactHolderRepository.findContactHolderByDeviceId(deviceId);

		if (optional.isEmpty()) {
			throw new NotFoundException();
		} else {
			ContactHolder holder = optional.get();
			holder.getContacts().sort(Comparator.comparing(Contact::getFirstName, String::compareToIgnoreCase));
			return holder;
		}
	}

	public Object findTexts(String deviceId) {

		Optional<Texts> optional = textsRepository.findTextsByDeviceId(deviceId);

		if (optional.isEmpty()) {
			throw new NotFoundException();
		} else {
			List<TextThread> textThreads = optional.get().getTextThreads();
			textThreads.sort(Comparator.comparing(TextThread::getTimeCreated).reversed());
			textThreads.stream().forEach(t -> {
				t.getMessages().sort(Comparator.comparing(TextMessage::getTimeSent).reversed());
			});
			return textThreads;
		}

	}

	@Override
	public void update(String resource, String deviceId, List<Map<Object, Object>> listData) {
		if (resource.equals("texts")) {
			updateTexts(deviceId, listData);
		} else if (resource.equals("contacts")) {
			updateContacts(deviceId, listData);
		} else if (resource.equals("call-logs")) {
			updateCallLogs(deviceId, listData);
		} else {
			throw new BadRequestException("Bad resource");
		}
	}

	private void updateCallLogs(String deviceId, List<Map<Object, Object>> listData) {

		Optional<CallLogHolder> optional = callLogHolderRepository.findCallLogHolderByDeviceId(deviceId);
		CallLogHolder holder = null;
		List<CallLog> callLogs = null;

		if (optional.isEmpty()) {
			holder = new CallLogHolder();
			holder.setDeviceId(deviceId);
			callLogs = new ArrayList<>();
		} else {
			holder = optional.get();
			callLogs = holder.getCallLogs();
		}
		for (Map<Object, Object> data : listData) {
			CallLog callLog = new CallLog();
			callLog.setId((long) callLogs.size());
			if (data.get("firstName") != null) {
				callLog.setFirstName(data.get("firstName").toString());
			}
			if (data.get("lastName") != null) {
				callLog.setLastName(data.get("lastName").toString());
			}
			if (data.get("phoneNumber") != null) {
				callLog.setPhoneNumber(data.get("phoneNumber").toString());
			}
			if (data.get("callStatus") != null) {
				callLog.setCallStatus(data.get("callStatus").toString());
			}
			if (data.get("callDirection") != null) {
				callLog.setCallDirection(data.get("callDirection").toString());
			}
			if (data.get("timeCalled") != null) {
				callLog.setTimeCalled(data.get("timeCalled").toString());
			}
			if (data.get("callDuration") != null) {
				callLog.setCallDuration(Long.valueOf(data.get("callDuration").toString()));
			}
			callLogs.add(callLog);
		}

		holder.setLastBackup(Date.from(Instant.now()));
		holder.setCallLogs(callLogs);
		callLogHolderRepository.save(holder);
	}

	private void updateContacts(String deviceId, List<Map<Object, Object>> listData) {

		Optional<ContactHolder> optional = contactHolderRepository.findContactHolderByDeviceId(deviceId);
		ContactHolder holder = null;
		List<Contact> contacts = null;

		if (optional.isEmpty()) {
			holder = new ContactHolder();
			holder.setDeviceId(deviceId);
			contacts = new ArrayList<>();
		} else {
			holder = optional.get();
			contacts = holder.getContacts();
		}

		for (Map<Object, Object> data : listData) {
			Contact contact = new Contact();
			contact.setId((long) contacts.size());
			if (data.get("firstName") != null) {
				contact.setFirstName(data.get("firstName").toString());
			}
			if (data.get("lastName") != null) {
				contact.setLastName(data.get("lastName").toString());
			}
			if (data.get("phoneNumber") != null) {
				contact.setPhoneNumber(data.get("phoneNumber").toString());
			}
			contacts.add(contact);
		}
		holder.setLastBackup(Date.from(Instant.now()));
		holder.setContacts(contacts);
		contactHolderRepository.save(holder);
	}

	private void updateTexts(String deviceId, List<Map<Object, Object>> listData) {

		Optional<Texts> optional = textsRepository.findTextsByDeviceId(deviceId);
		Texts texts = null;
		List<TextThread> textThreads = null;

		if (optional.isEmpty()) {
			texts = new Texts();
			textThreads = new ArrayList<>();
			texts.setDeviceId(deviceId);
		} else {
			texts = optional.get();
			textThreads = texts.getTextThreads();
		}

		for (Map<Object, Object> data : listData) {

			Optional<TextThread> opthread = Optional.empty();
			List<TextMessage> messages = null;
			TextThread thread = null;
			boolean threadExist = false;

			if (data.get("id") != null) {
				opthread = textThreads.stream().filter(t -> t.getId() == Integer.valueOf(data.get("id").toString()))
						.findFirst();
			}

			if (opthread.isEmpty()) {
				thread = new TextThread();
				messages = new ArrayList<>();
				thread.setId(textThreads.size());
				thread.setTimeCreated(Date.from(Instant.now()));
			} else {
				threadExist = true;
				thread = opthread.get();
				messages = thread.getMessages();
			}

			if (data.get("messages") != null) {
				@SuppressWarnings("unchecked")
				List<Map<String, String>> listMap = (List<Map<String, String>>) data.get("messages");

				for (Map<String, String> map : listMap) {
					TextMessage msg = new TextMessage();

					if (map.get("inBound") != null && map.get("message") != null) {
						msg.setId((long) messages.size());
						msg.setInBound(map.get("inBound"));
						msg.setMessage(map.get("message").toString());
						msg.setTimeSent(Date.from(Instant.now()));
						messages.add(msg);
					}
				}
			}

			if (data.get("receiverName") != null) {
				thread.setReceiverName(data.get("receiverName").toString());
			}
			if (data.get("receiverPhone") != null) {
				thread.setReceiverPhone(data.get("receiverPhone").toString());
			}

			thread.setLastBackup(Date.from(Instant.now()));
			thread.setMessages(messages);

			if (threadExist) {
				textThreads.set(thread.getId(), thread);
			} else {
				textThreads.add(thread);
			}

		}
		texts.setTextThreads(textThreads);
		textsRepository.save(texts);
	}

	@Override
	public void deleteAll(String resource, String deviceId) {
		if (resource.equals("texts")) {
			textsRepository.deleteByDeviceId(deviceId);
		} else if (resource.equals("contacts")) {
			contactHolderRepository.deleteByDeviceId(deviceId);
		} else if (resource.equals("call-logs")) {
			callLogHolderRepository.deleteByDeviceId(deviceId);
		} else {
			throw new BadRequestException("Bad resource");
		}

	}

	@Override
	public void delete(String resource, String deviceId, Long id) {
		if (resource.equals("texts")) {
			deleteText(deviceId, id);
		} else if (resource.equals("contacts")) {
			deleteContact(deviceId, id);
		} else if (resource.equals("call-logs")) {
			deleteCallLog(deviceId, id);
		} else {
			throw new BadRequestException("Bad resource");
		}

	}

	private void deleteCallLog(String deviceId, Long id) {
		Optional<CallLogHolder> optional = callLogHolderRepository.findCallLogHolderByDeviceId(deviceId);

		if (optional.isPresent()) {
			CallLogHolder holder = optional.get();
			List<CallLog> callLogs = holder.getCallLogs();
			callLogs.removeIf(c -> c.getId().equals(id));
			holder.setCallLogs(callLogs);
			callLogHolderRepository.save(holder);
		}

	}

	private void deleteContact(String deviceId, Long id) {
		Optional<ContactHolder> optional = contactHolderRepository.findContactHolderByDeviceId(deviceId);

		if (optional.isPresent()) {
			ContactHolder holder = optional.get();
			List<Contact> contacts = holder.getContacts();
			contacts.removeIf(c -> c.getId().equals(id));
			holder.setContacts(contacts);
			contactHolderRepository.save(holder);
		}
	}

	private void deleteText(String deviceId, Long id) {
		Optional<Texts> optional = textsRepository.findTextsByDeviceId(deviceId);

		if (optional.isPresent()) {
			Texts texts = optional.get();
			List<TextThread> textThreads = texts.getTextThreads();
			textThreads.removeIf(t -> t.getId() == id.intValue());
			texts.setTextThreads(textThreads);
			textsRepository.save(texts);
		}

	}

}
